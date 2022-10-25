// gcc race.c -lpthread && ./a.out
// gcc race.c -lpthread -S
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

int SZ = 100;
// int SZ = 1000000;
int c = 0;
void* ctr() {
    int i;
    for(i=0; i<SZ; i++) {   
        c+=2;
        // printf(" %d", c); 
    }
}


int main() {
    int rt1, rt2;
    pthread_t t1, t2; 

    /* Create two threads */
    if( (rt1=pthread_create( &t1, NULL, &ctr, NULL)) )
        printf("Thread creation failed: %d\n", rt1);
    if( (rt2=pthread_create( &t2, NULL, &ctr, NULL)) )
        printf("Thread creation failed: %d\n", rt2);

    /* Wait for both threads to finish */
    pthread_join(t1, NULL);
    pthread_join(t2, NULL);
    printf(" %d\n", c);
    return 0;

}