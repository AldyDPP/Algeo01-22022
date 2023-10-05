package tubes.bonus;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import tubes.matrix.src.Matrix;
import tubes.matrix.src.SquareMatrix;

public class BonusRGB{

    SquareMatrix D;
    SquareMatrix X;
    double zoomFactor;
    double[][][] originPicture;
    int originHeight;
    int originWidth;
    double[][][] enlargedPicture;
    double xBuffer = 0;
    double yBuffer = 0;

    void loadPixelsToGrayscale(String path) throws IOException {

        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        originPicture = new double[image.getWidth() + 3][image.getHeight() + 3][3];
        originHeight = image.getHeight();
        originWidth = image.getWidth();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                originPicture[x][y][0] = color.getRed();
                originPicture[x][y][1] = color.getGreen();
                originPicture[x][y][2] = color.getBlue();
            }
        }

        EdgeHandling();
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
        enlargedPicture = new double[(int)Math.floor(originWidth*zoomFactor)][(int)Math.floor(originHeight*zoomFactor)][3];
    }

    void workOnFourPixels(int x, int y, int k){
        Matrix I = getIMatrix(x, y, k);
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
                [k] = calculatePixel(A, newX, newY);
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
            int sum = 0;
            yBuffer = 0;
            xBuffer += zoomFactor;
            for(int y = 0; y < originHeight; y++){
                yBuffer += zoomFactor;
                sum += (int)Math.floor(yBuffer);
                for(int i = 0; i < 3; i++){
                    workOnFourPixels(x, y, i);
                }
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
                Color newVal = new Color(makeInRange((int)enlargedPicture[y][x][0]), 
                makeInRange((int)enlargedPicture[y][x][1]), 
                makeInRange((int)enlargedPicture[y][x][2]));
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

    protected int makeInRange(int col){
        int res = Math.min(col, 255);
        res = Math.max(res, 0);
        return res;
    }

    protected double makeInRange(double col){
        double res = Math.min(col, (double)255);
        res = Math.max(res, (double)0);
        return res;
    }

    protected void EdgeHandling(){
        for(int i = 0; i < originHeight; i++){
            for(int j = 0; j < 3; j++){
                overEdgeWidthValue(originWidth, i, j);
                overEdgeWidthValue(originWidth + 1, i, j);
                underEdgeWidthValue(originWidth + 2, i, j);
            }
        }

        for(int i = 0; i < originWidth; i++){
            for(int j = 0; j < 3; j++){
                overEdgeHeightValue(i, originHeight, j);
                overEdgeHeightValue(i, originHeight + 1, j);
                underEdgeHeightValue(i, originHeight + 2, j);
            }
        }
        for(int j = 0; j < 3; j++){
            for(int extraW = 0; extraW < 3; extraW++){
                for(int extraH = 0; extraH < 3; extraH++){
                    overEdgeHeightValue(originWidth + extraW, originHeight + extraH, j);
                }
            }
        }
    }

    protected void overEdgeWidthValue(int x, int y, int i){
        originPicture[x][y][i] = makeInRange(3*originPicture[x - 1][y][i] 
               - 3*originPicture[x - 2][y][i] 
               + originPicture[x - 3][y][i]);
    }

    protected void overEdgeHeightValue(int x, int y, int i){
        originPicture[x][y][i] = makeInRange(3*originPicture[x][y - 1][i] 
               - 3*originPicture[x][y - 2][i] 
               + originPicture[x][y - 3][i]);
    }

    protected void underEdgeWidthValue(int x, int y, int i){
        originPicture[x][y][i] = makeInRange(3*originPicture[0][y][i] 
               - 3*originPicture[1][y][i] 
               + originPicture[2][y][i]);
    }

    protected void underEdgeHeightValue(int x, int y, int i){
        originPicture[x][y][i] = makeInRange(3*originPicture[x][0][i] 
               - 3*originPicture[x][1][i] 
               + originPicture[x][2][i]);
    }

    protected Matrix getIMatrix(int x, int y, int k){
        Matrix I = new Matrix();
        I.initializeMatrix(16, 1);
        for(int i = -1; i < 3; i++){
            for(int j = -1; j < 3; j++){
                int xIdx = (x + i >= 0) ? (x + i) : originWidth + 1;
                int yIdx = (y + j >= 0) ? (y + j) : originHeight + 1;
                I.contents[xyToIDX(i, j)][0] = originPicture[xIdx][yIdx][k];
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