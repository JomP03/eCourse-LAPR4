CC=gcc
CFLAGS=-Wall -Wextra -g -lrt -lpthread
SRC_DIR=./src
OBJ_DIR=./obj
BIN_DIR=./bin
TARGET=program

SRCS=$(wildcard $(SRC_DIR)/*.c)
OBJS=$(patsubst $(SRC_DIR)/%.c,$(OBJ_DIR)/%.o,$(SRCS))

.PHONY: all clean run

a: $(BIN_DIR)/$(TARGET)

$(BIN_DIR)/$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) $^ -o $@

$(OBJ_DIR)/%.o: $(SRC_DIR)/%.c
	$(CC) $(CFLAGS) -c $< -o $@

r: $(BIN_DIR)/$(TARGET)
	./$(BIN_DIR)/$(TARGET)

c:
	rm -f $(OBJ_DIR)/*.o $(BIN_DIR)/$(TARGET)
