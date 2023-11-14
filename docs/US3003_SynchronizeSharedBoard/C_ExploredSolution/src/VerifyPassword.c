#include <stdio.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <semaphore.h>
#include <stdlib.h>
#include <string.h>

int verifyPassword() {
    char password[20];
    printf("\nEnter the password: ");
    scanf("%s", password);

    // Read the password from the file
    FILE *file = fopen("src/password.txt", "r");

    if (file == NULL)
    {
        perror("Error opening the file");
        return EXIT_FAILURE;
    }

    char correctPassword[20];

    if (fgets(correctPassword, 20, file) == NULL)
    {
        perror("Error reading the file");
        return EXIT_FAILURE;
    }

    // Compare the passwords
    if (strcmp(password, correctPassword) != 0)
    {
        printf("Incorrect password\n");
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}