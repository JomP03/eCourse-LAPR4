#ifndef STRUCT_SHARED_BOARD_H
#define STRUCT_SHARED_BOARD_H

#include <stdio.h>
#include "StructPostit.h"

#define MAX_NUMBER_ROWS 20
#define MAX_NUMBER_COLUMNS 10

typedef struct
{
    Postit cells[MAX_NUMBER_ROWS][MAX_NUMBER_COLUMNS];
    int numberRows;
    int numberColumns;
} SharedBoard;
#endif