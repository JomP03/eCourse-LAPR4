#include <stdio.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <semaphore.h>
#include <time.h>
#include <stdlib.h>

#define NUMBER_OPTIONS 4

/**
 * This function is responsible for presenting the main menu to the user and returning the selected option.
*/
int mainMenu()
{
    // Possible options
    char *options[NUMBER_OPTIONS] = {
        "Edit a post-it",
        "View a post-it",
        "Clear the board",
        "Exit"};

    // Print the options
    printf("\n");

    // Title
    printf("-------\n|Board|\n-------\n\n");

    for (int i = 0; i < NUMBER_OPTIONS; i++)
    {
        if (i == NUMBER_OPTIONS - 1)
        {
            printf("%d - %s\n\n", 0, options[i]);
        }
        else
        {
            printf("%d - %s\n", i + 1, options[i]);
        }
    }

    // Get the selected option
    int selectedOption = -1;

    while (selectedOption < 0 || selectedOption > NUMBER_OPTIONS)
    {
        printf("Select an option: ");
        scanf("%d", &selectedOption);
    }

    return selectedOption;
}