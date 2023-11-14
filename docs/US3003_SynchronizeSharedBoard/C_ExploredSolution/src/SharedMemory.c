#include <stdio.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdlib.h>
#include <errno.h>
#include "StructSharedBoard.h"
#include "StructPostit.h"

/**
 * This function is responsible for creating the shared memory (if it doesn't exist) and mapping it to the
 * shared_board variable.
 */
int createSharedMemory(size_t size, int *shmfd, SharedBoard **shared_board)
{
    *shmfd = shm_open("/shm_board", O_CREAT | O_RDWR, S_IRUSR | S_IWUSR);
    if (*shmfd < 0)
    {
        if (errno == EEXIST)
        {
            *shmfd = shm_open("/shm_board", O_RDWR, S_IRUSR | S_IWUSR);
        }

        if (*shmfd < 0)
        {
            perror("Failed to create shared memory!");
            return EXIT_FAILURE;
        }
    }

    if (ftruncate(*shmfd, size) < 0)
    {
        perror("Failed to truncate shared memory!");
        return EXIT_FAILURE;
    }

    *shared_board = mmap(NULL, size, PROT_READ | PROT_WRITE, MAP_SHARED, *shmfd, 0);

    if (*shared_board == MAP_FAILED)
    {
        perror("Failed to map shared memory!");
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}