#include <stdio.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdlib.h>
#include <errno.h>
#include <semaphore.h>

#define MAX_NUM_SEMAPHORES 200
#define SEMAPHORE_NAME_LENGTH 30
#define MUTEX_SEMAPHORE 1

/**
 * This function is responsible for creating the semaphores.
 */
int createSemaphore(sem_t **semaphores, int numberSemaphores)
{
    char semaphoreName[SEMAPHORE_NAME_LENGTH];

    // Create semaphores
    for (int i = 0; i < numberSemaphores; i++)
    {
        snprintf(semaphoreName, SEMAPHORE_NAME_LENGTH, "/sem_cell_%d", i);
        if ((semaphores[i] = sem_open(semaphoreName, O_CREAT, 0644, MUTEX_SEMAPHORE)) == SEM_FAILED)
        {
            if (errno == EEXIST)
            {
                semaphores[i] = sem_open(semaphoreName, O_CREAT);
            }

            if (semaphores[i] == SEM_FAILED)
            {
                perror("Failed to create semaphore");
                return EXIT_FAILURE;
            }
        }
    }

    return EXIT_SUCCESS;
}
