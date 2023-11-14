#include <stdio.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <semaphore.h>
#include <time.h>
#include <stdlib.h>
#include <errno.h>
#include "SharedMemory.h"
#include "StructSharedBoard.h"
#include "StructPostit.h"
#include "Semaphore.h"
#include "MainMenu.h"
#include "EditPostit.h"	
#include "ViewPostit.h"
#include "ClearSharedBoard.h"

// Constants
#define NUMBER_ROWS 3
#define NUMBER_COLUMNS 3
#define NUMBER_SEMAPHORES 9
#define SEMAPHORE_NAME_LENGTH 30

int main(void)
{
    // Shared memory - Contains the shared board
    int shmfd;
    SharedBoard *shared_board = NULL;

    if (createSharedMemory(sizeof(SharedBoard), &shmfd, &shared_board) == EXIT_FAILURE)
    {
        return EXIT_FAILURE;
    }

    // Initialize the shared board
    shared_board->numberRows = NUMBER_ROWS;
    shared_board->numberColumns = NUMBER_COLUMNS;

    // Semaphores - All semaphores are mutex, to restrain access to the shared memory (post-its)
    sem_t *semaphores[NUMBER_SEMAPHORES];

    if (createSemaphore(semaphores, NUMBER_SEMAPHORES) == EXIT_FAILURE)
    {
        return EXIT_FAILURE;
    }

    int selectedOption;

    do
    {
        selectedOption = mainMenu();

        switch (selectedOption)
        {
        case 1:
            // Edit a post-it
            editPostit(shared_board, semaphores);
            break;

        case 2:
            // View a post-it
            viewPostit(shared_board);
            break;

        case 3:
            // Unlink the shared memory and semaphores
            if (clearBoard(&shmfd, &shared_board, semaphores, shared_board->numberRows, shared_board->numberColumns) != EXIT_SUCCESS) {
                break;
            }
            printf("\nExiting...\n");
            return EXIT_SUCCESS;

        case 0:
            // Exit
            printf("\nExiting...\n");
            return EXIT_SUCCESS;
        }

        // Optional - Clear the screen
        // Clear stdin
        // sleep(1.5);
        // system("clear");

    } while (selectedOption != 0);

    return EXIT_SUCCESS;
}