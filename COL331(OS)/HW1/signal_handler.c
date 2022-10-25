// C program that does not suspend when CTRL+C  is pressed and prints hello
// Ctrl+C is pressed
#include <stdio.h>
#include <signal.h>


void sighandler(int sig_num)
{
	// Reset handler to catch SIGINT next time
	signal(SIGINT, sighandler);
	printf("hello ");
	fflush(stdout);
}

int main(int argc, char **argv)
{
	// Set the SIGINT (Ctrl-C) signal handler
	
	signal(SIGINT, sighandler);
	while(1)
	{
		
	}
	return 0;
}
