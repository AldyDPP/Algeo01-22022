package tubes.bonus;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import tubes.matrix.src.Matrix;
import tubes.matrix.src.SquareMatrix;

public class Bonus{

    SquareMatrix D;
    SquareMatrix X;
    double zoomFactor;
    Matrix originPicture;
    int originHeight;
    int originWidth;
    double[][] enlargedPicture;
    double xBuffer = 0;
    double yBuffer = 0;

    void loadPixelsToGrayscale(String path) throws IOException {

        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        originPicture = new Matrix();
        originPicture.initializeMatrix(image.getWidth() + 3, image.getHeight() + 3);
        originHeight = image.getHeight();
        originWidth = image.getWidth();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                originPicture.contents[x][y] = 0.3*color.getRed() + 0.59*color.getGreen() + 0.11*color.getBlue();
            }
        }
    }

    void constructDMatrix(){
        D = new SquareMatrix();
        D.initializeMatrix(16, 16);
        for(int y = 0; y < 2; y++){
            for(int x = 0; x < 2; x++){
                D.contents[x + 2*y][xyToIDX(x, y)] += (double)1;
                D.contents[4 + x + 2*y][xyToIDX(x + 1, y)] += (double)0.5;
                D.contents[4 + x + 2*y][xyToIDX(x - 1, y)] -= (double)0.5;
                D.contents[8 + x + 2*y][xyToIDX(x, y + 1)] += (double)0.5;
                D.contents[8 + x + 2*y][xyToIDX(x, y - 1)] -= (double)0.5;
                D.contents[12 + x + 2*y][xyToIDX(x + 1, y + 1)] += (double)0.25;
                D.contents[12 + x + 2*y][xyToIDX(x - 1, y + 1)] -= (double)0.25;
                D.contents[12 + x + 2*y][xyToIDX(x + 1, y - 1)] -= (double)0.25;
                D.contents[12 + x + 2*y][xyToIDX(x - 1, y - 1)] += (double)0.25;
            }
        }
    }

    void createXMatrix(){
        X = new SquareMatrix();
        X.initializeMatrix(16, 16);
        for(int y = 0; y < 2; y++){
            for(int x = 0; x < 2; x++){
                int baseRowIdx = x + 2*y; // Map (0, 0) -> 0, (1, 0) -> 1, etc.
                for(int j = 0; j < 4; j++){
                    for(int i = 0; i < 4; i++){
                        X.contents[baseRowIdx][4*j + i] = Math.pow(x, i)*Math.pow(y, j);
                        X.contents[4 + baseRowIdx][4*j + i] = i*Math.pow(x, (i > 1) ? i-1 : 0)*Math.pow(y, j);
                        X.contents[8 +baseRowIdx][4*j + i] = j*Math.pow(x, i)*Math.pow(y, (j > 1) ? j - 1 : 0);
                        X.contents[12 + baseRowIdx][4*j + i] = i*j*Math.pow(x, (i > 1) ? i-1 : 0)*Math.pow(y, (j > 1) ? j - 1 : 0);
                    }
                }
            }
        }
    }
    void createResArray(){
        enlargedPicture = new double[(int)Math.floor(originWidth*zoomFactor)][(int)Math.floor(originHeight*zoomFactor)];
    }

    void workOnFourPixels(int x, int y){
        Matrix I = getIMatrix(x, y);
        Matrix A = new Matrix();
        A.initializeMatrix(16, 1);
        A = Matrix.multiplyMatrix(D, I);
        A = Matrix.multiplyMatrix(X.inverseByERO(), A);
        int i = 0;
        int j = 0;
        double yBuffer_save = yBuffer;
        double xBuffer_save = xBuffer;
        while(Float.compare((float)xBuffer_save, (float)1) >= 0){
            while(Float.compare((float)yBuffer_save, (float)1) >= 0){
                double newX = i/(zoomFactor);
                double newY = j/(zoomFactor);
                enlargedPicture[(int)(zoomFactor*x) + i]
                [(int)(zoomFactor*y) + j]
                = calculatePixel(A, newX, newY);
                j++;
                yBuffer_save -= 1;
            }
            i++;
            xBuffer_save -= 1;
            yBuffer_save = yBuffer;
            j = 0;
        }
    }

    void workOnAllPixels(){
        createResArray();
        for(int x = 0; x < originWidth; x++){
            yBuffer = 0;
            xBuffer += zoomFactor;
            for(int y = 0; y < originHeight; y++){
                yBuffer += zoomFactor;
                workOnFourPixels(x, y);
                yBuffer -= Math.floor(yBuffer);
            }
            xBuffer -= Math.floor(xBuffer);
        }
    }

    void writeImage(String Name) {
        String path = Name + ".png";
        BufferedImage image = new BufferedImage((int)(zoomFactor*originWidth), 
                                                (int)(zoomFactor*originHeight), 
                                                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < (int)(zoomFactor*originHeight); x++) {
            for (int y = 0; y < (int)(zoomFactor*originWidth); y++) {
                Color newVal = new Color(makeInRange((int)enlargedPicture[y][x]), 
                makeInRange((int)enlargedPicture[y][x]), 
                makeInRange((int)enlargedPicture[y][x]));
                image.setRGB(y, x, newVal.getRGB());
            }
        }
        
        File ImageFile = new File(path);
        try {
            ImageIO.write(image, "png", ImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getImageAndZoom(String path, double factor, String resPath) throws IOException{
        zoomFactor = factor;
        loadPixelsToGrayscale(path);
        constructDMatrix();
        createXMatrix();
        createResArray();
        workOnAllPixels();
        writeImage(resPath);
    }

    protected double makeInRange(double col){
        double res = Math.min(col, (double)255);
        res = Math.max(res, (double)0);
        return res;
    }

    protected int makeInRange(int col){
        int res = Math.min(col, 255);
        res = Math.max(res, 0);
        return res;
    }

    protected void EdgeHandling(){
        for(int i = 0; i < originHeight; i++){
            overEdgeWidthValue(originWidth, i);
            overEdgeWidthValue(originWidth + 1, i);
            underEdgeWidthValue(originWidth + 2, i);
        }

        for(int i = 0; i < originWidth; i++){
            overEdgeHeightValue(i, originHeight);
            overEdgeHeightValue(i, originHeight + 1);
            underEdgeHeightValue(i, originHeight + 2);
        }
        for(int extraW = 0; extraW < 3; extraW++){
            for(int extraH = 0; extraH < 3; extraH++){
                overEdgeHeightValue(originWidth + extraW, originHeight + extraH);
            }
        }
    }

    protected void overEdgeWidthValue(int x, int y){
        originPicture.contents[x][y] = makeInRange(3*originPicture.contents[x - 1][y] 
               - 3*originPicture.contents[x - 2][y] 
               + originPicture.contents[x - 3][y]);
    }

    protected void overEdgeHeightValue(int x, int y){
        originPicture.contents[x][y] = makeInRange(3*originPicture.contents[x][y - 1] 
               - 3*originPicture.contents[x][y - 2] 
               + originPicture.contents[x][y - 3]);
    }

    protected void underEdgeWidthValue(int x, int y){
        originPicture.contents[x][y] = makeInRange(3*originPicture.contents[0][y] 
               - 3*originPicture.contents[1][y] 
               + originPicture.contents[2][y]);
    }

    protected void underEdgeHeightValue(int x, int y){
        originPicture.contents[x][y] = makeInRange(3*originPicture.contents[x][0] 
               - 3*originPicture.contents[x][1] 
               + originPicture.contents[x][2]);
    }

    protected Matrix getIMatrix(int x, int y){
        Matrix I = new Matrix();
        I.initializeMatrix(16, 1);
        for(int i = -1; i < 3; i++){
            for(int j = -1; j < 3; j++){
                int xIdx = (x + i >= 0) ? (x + i) : originWidth + 1;
                int yIdx = (y + j >= 0) ? (y + j) : originHeight + 1;
                I.contents[xyToIDX(i, j)][0] = originPicture.contents[xIdx][yIdx];
            }
        }
        return I;
    }

    protected int xyToIDX(int x, int y){
        return ((x + 1) + 4*(y + 1));
    }

    protected double calculatePixel(Matrix A, double x, double y){
        double res = 0;
        for(int j = 0; j < 4; j++){
            for(int i = 0; i < 4; i++){
                res += A.contents[i + 4*j][0] * Math.pow(x, i) * Math.pow(y, j);
            }
        }
        return res;
    }
    
}