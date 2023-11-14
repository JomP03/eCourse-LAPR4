#include <stdio.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <semaphore.h>
#include <stdlib.h>
#include <string.h>
#include "StructSharedBoard.h"
#include "StructPostit.h"
#include "VerifyPassword.h"

#define SEMAPHORE_NAME_LENGTH 30

/**
 * This function clears the shared board and end the program.
*/
int clearBoard(int *shmfd, SharedBoard **shared_board, sem_t **semaphores, int numberRows, int numberColumns)
{
    // Request the password
    int owner = verifyPassword();

    if (owner == EXIT_FAILURE)
    {
        return EXIT_FAILURE;
    }
    
    // Clear the shared memory
    if (munmap(*shared_board, sizeof(SharedBoard)) == -1)
    {
        perror("Error unmapping the shared memory");
        return EXIT_FAILURE;
    }

    // Close the shared memory
    if (close(*shmfd) == -1)
    {
        perror("Error closing the shared memory");
        return EXIT_FAILURE;
    }

    // Unlink the shared memory
    if (shm_unlink("/shm_board") == -1)
    {
        perror("Error unlinking the shared memory");
        return EXIT_FAILURE;
    }

    // Clear the semaphores
    for (int i = 0; i < numberRows * numberColumns; i++)
    {
        if (sem_close(semaphores[i]) == -1)
        {
            perror("Error closing the semaphore");
            return EXIT_FAILURE;
        }
    }

    // Unlink the semaphores
    for (int i = 0; i < numberRows * numberColumns; i++)
    {
        char semaphoreName[SEMAPHORE_NAME_LENGTH];
        sprintf(semaphoreName, "/sem_cell_%d", i);
        if (sem_unlink(semaphoreName) == -1)
        {
            perror("Error unlinking the semaphore");
            return EXIT_FAILURE;
        }
    }

    return EXIT_SUCCESS;
}