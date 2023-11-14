#include <stdio.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <semaphore.h>
#include <time.h>
#include <stdlib.h>

/* Create child processes */

pid_t child_buider(int num_childs)
{
    pid_t cpid;

    for (int child = 0; child < num_childs; child++)
    {
        if ((cpid = fork()) < 0)
        {
            exit(EXIT_FAILURE);
        }

        if (cpid == 0)
        {
            return child;
        }
    }

    return -1;
}

/* Equal to sem_wait() and sem_post() but with built in verification */

void up(sem_t *sem)
{
    if (sem_post(sem) == -1)
    {
        perror("Error at sem_post().");
        exit(0);
    }
}

void down(sem_t *sem)
{
    if (sem_wait(sem) == -1)
    {
        perror("Error at sem_wait().");
        exit(0);
    }
}