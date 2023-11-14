#ifndef STRUCT_POSTIT_H
#define STRUCT_POSTIT_H

#include <stdio.h>

#define CONTENT_LENGTH 100

typedef struct
{
    char content[CONTENT_LENGTH];
} Postit;
#endif