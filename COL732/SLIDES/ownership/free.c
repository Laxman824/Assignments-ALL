// gcc free.c && ./a.out
// 3
/* Problem:
   v1, v2, v3 -> All point to the same location in heap
 */

#include <stdio.h>
#include <stdlib.h>

int main(int argc, char** argv) {
	int* v1 = (int*) malloc(sizeof(int));
	*v1 = 53;
	free(v1);

	int* v2 = (int*) malloc(sizeof(int));
	*v2 = 25;

	free(v1);

	int* v3 = (int*) malloc(sizeof(int));
	*v3 = 59;

	printf("%d\n", *v2);
}