a = float(input("Masukkan nilai a: "))
b = float(input("Masukkan nilai b: "))
delta = float(input("Masukkan nilai delta: "))

luas = 0
while a < b:

    sisi_1 = (a**3 + a + 1)
    sisi_2 = (b**3 + b + 1)


    luas += (sisi_1 + sisi_2)* (delta)/2
    a += delta

print(luas)