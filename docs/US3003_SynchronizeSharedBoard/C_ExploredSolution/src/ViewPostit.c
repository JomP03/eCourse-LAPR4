#include <stdio.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <semaphore.h>
#include <stdlib.h>
#include "StructSharedBoard.h"
#include "StructPostit.h"

/**
 * This function is responsible for presenting the post-it content to the user.
 */
void viewPostit(SharedBoard *shared_board)
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

    // Get the post-it and print its content
    Postit postit = shared_board->cells[row - 1][column - 1];

    printf("\nPost-it content: %s\n", postit.content);

    sleep(1);
}