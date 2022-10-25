/* A C program that does not terminate when Ctrl+C is pressed */
#include <stdio.h>
#include <signal.h>

/* Signal Handler for SIGINT */
void sigintHandler(int sig_num)
{
	
	signal(SIGINT, sigintHandler);
	printf("\n Cannot be terminated using Ctrl+C \n");
	fflush(stdout);
}

int main ()
{
	
	signal(SIGINT, sigintHandler);

	/* Infinite loop */
	while(1)
	{		
	}
	return 0;
}

