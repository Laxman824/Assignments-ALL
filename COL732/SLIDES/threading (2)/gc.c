// gcc mutex.c -lpthread && ./a.out
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

// int SZ = 100;
// int SZ = 1000000;
int SZ = 10;

struct Count {
    int c;
    pthread_mutex_t c_mutex;
};

void* ctr(void* args) {
    struct Count* count = (struct Count*) args;

    int i;
    for(i=0; i<SZ; i++) {   
        pthread_mutex_lock(&(count->c_mutex));
        count->c += 2;
        pthread_mutex_unlock(&(count->c_mutex));
        printf(" %d\n", count->c); 
    }
    printf("Finished thread \n"); 
}


void* start_threads() {
    int c = 0;
    pthread_mutex_t c_mutex;

    int rt1, rt2;
    pthread_t t1, t2; 
    
    struct Count *count = (struct Count*) malloc(sizeof(struct Count));
    pthread_mutex_init(&(count->c_mutex), NULL);

    /* Create two threads */
    if( (rt1=pthread_create( &t1, NULL, &ctr, count)) )
        printf("Thread creation failed: %d\n", rt1);
    else
        printf("Created first worker thread\n");
    if( (rt2=pthread_create( &t2, NULL, &ctr, count)) )
        printf("Thread creation failed: %d\n", rt2);
    else
        printf("Created second worker thread\n");

    // free(count);
}

int main() {
    int rt;
    pthread_t t;

    if( (rt=pthread_create( &t, NULL, &start_threads, NULL)) )
        printf("Thread creation failed: %d\n", rt);
    else
        printf("Created starter thread\n");
    while(1);
}