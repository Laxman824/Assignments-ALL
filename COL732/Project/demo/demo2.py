'''
donut code in python
/*
gcc -o donut donut.c -lm
*/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>
#include <unistd.h>

int k;
double sin(), cos();
int main()
{
    float A = 0, B = 0, i, j, z[1760];
    char b[1760];
    system("cls");
    for (;;)
    {
        memset(b, 32, 1760);
        memset(z, 0, 7040);
        for (j = 0; 6.28 > j; j += 0.07)
        {
            for (i = 0; 6.28 > i; i += 0.02)
            {
                float sini = sin(i),
                      cosj = cos(j),
                      sinA = sin(A),
                      sinj = sin(j),
                      cosA = cos(A),
                      cosj2 = cosj + 2,
                      mess = 1 / (sini * cosj2 * sinA + sinj * cosA + 5),
                      cosi = cos(i),
                      cosB = cos(B),
                      sinB = sin(B),
                      t = sini * cosj2 * cosA - sinj * sinA;
                int x = 40 + 30 * mess * (cosi * cosj2 * cosB - t * sinB),
                    y = 12 + 15 * mess * (cosi * cosj2 * sinB + t * cosB),
                    o = x + 80 * y,
                    N = 8 * ((sinj * sinA - sini * cosj * cosA) * cosB - sini * cosj * sinA - sinj * cosA - cosi * cosj * sinB);
                if (22 > y && y > 0 && x > 0 && 80 > x && mess > z[o])
                {
                    z[o] = mess;
                    b[o] = ".,-~:;=!*#$@"[N > 0 ? N : 0];
                }
            }
        }
        printf("\x1b[d");
        for (k = 0; 1761 > k; k++)
            putchar(k % 80 ? b[k] : 10);
        A += 0.08;
        B += 0.06;
        // wait 0.1 second
        usleep(100000);
    }
}
'''
import math
import time
k = 0
def sin(x):
    return math.sin(x)
def cos(x):
    return math.cos(x)
def main():
    A = 0
    B = 0
    i = 0
    j = 0
    z = [0 for x in range(1760)]
    b = [' ' for x in range(1760)]
    while(True):
        for x in range(1760):
            b[x] = ' '
        for x in range(1760):
            z[x] = 0
        j=0
        while(6.28 > j):
            i=0
            while(6.28 > i):
                sini = sin(i)
                cosj = cos(j)
                sinA = sin(A)
                sinj = sin(j)
                cosA = cos(A)
                cosj2 = cosj + 2
                mess = 1 / (sini * cosj2 * sinA + sinj * cosA + 5)
                cosi = cos(i)
                cosB = cos(B)
                sinB = sin(B)
                t = sini * cosj2 * cosA - sinj * sinA
                x = int(40 + 30 * mess * (cosi * cosj2 * cosB - t * sinB))
                y = int(12 + 15 * mess * (cosi * cosj2 * sinB + t * cosB))
                o = int(x + 80 * y)
                # print(o)
                N = 8 * ((sinj * sinA - sini * cosj * cosA) * cosB - sini * cosj * sinA - sinj * cosA - cosi * cosj * sinB)
                N = int(N)
                if 22 > y and y > 0 and x > 0 and 80 > x and mess > z[o]:
                    z[o] = mess
                    b[o] = ".,-~:;=!*#$@"[N > 0 and N or 0]
                i += 0.02
            j += 0.07
        print("\x1b[d", end='')
        for k in range(1761):
            print(b[k] if k % 80 else '', end='')
        
        A += 0.08
        B += 0.06
        # wait 0.1 second
        # time.sleep(0.1)
if __name__ == '__main__':
    main()