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
#include "Tools.h"

#define MAX_CONTENT_LENGTH 100

/**
 * This function is responsible for editing a post-it.
 * As multiple users might try to edit the same post-it at the same time, we use semaphores to control the access
 * to the post-it.
 */
void editPostit(SharedBoard *shared_board, sem_t **semaphores)
{
    // Get the post-it coordinates
    int row, column;

    printf("\nEnter the post-it coordinates (row column): ");
    scanf("%d %d", &row, &column);

    // Check if the post-it exists
    while (row < 1 || row > shared_board->numberRows || column < 1 || column > shared_board->numberColumns)
    {
        printf("Invalid coordinates. Enter the post-it coordinates (row column): ");
        scanf("%d %d", &row, &column);
    }

    int index = (row - 1) * shared_board->numberColumns + (column - 1);

    // Get access to the post-it
    down(semaphores[index]);

    printf("\nEnter the new post-it content: ");

    // Clear stdin
    int c;
    while ((c = getchar()) != '\n' && c != EOF);

    fgets(shared_board->cells[row - 1][column - 1].content, MAX_CONTENT_LENGTH, stdin);
    shared_board->cells[row - 1][column - 1].content[strcspn(shared_board->cells[row - 1][column - 1].content, "\n")] = '\0'; // remove newline character

    printf("\nPost-it edited successfully by process %d.\n", getpid());

    up(semaphores[index]);

    sleep(1);
}