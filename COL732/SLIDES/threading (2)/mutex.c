// gcc mutex.c -lpthread && ./a.out
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

// int SZ = 100;
int SZ = 1000000;
int c = 0;
pthread_mutex_t c_mutex;

void* ctr() {
    int i;
    for(i=0; i<SZ; i++) {   
        pthread_mutex_lock(&c_mutex);
        c+=2;
        // c+=1;
        pthread_mutex_unlock(&c_mutex);
        // c+=1;
        // printf(" %d", c); 
    }
}


int main() {
    int rt1, rt2;
    pthread_t t1, t2; 

    pthread_mutex_init(&c_mutex, NULL);

    /* Create two threads */
    if( (rt1=pthread_create( &t1, NULL, &ctr, NULL)) )
        printf("Thread creation failed: %d\n", rt1);
    if( (rt2=pthread_create( &t2, NULL, &ctr, NULL)) )
        printf("Thread creation failed: %d\n", rt2);

    /* Wait for both threads to finish */
    pthread_join(t1, NULL);
    pthread_join(t2, NULL);
    
    // Do not need a mutex here?
    // pthread_mutex_lock(&c_mutex);
    printf(" %d\n", c);
    // pthread_mutex_unlock(&c_mutex);
    
    pthread_mutex_destroy(&c_mutex);
    return 0;

}