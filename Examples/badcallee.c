#include <stdio.h>

int foo(int x, int y, int z, int u, int v) {
    printf("%d %d %d %d %d\n", x, y, z , u, v);
    return x + y + z + u + v;
}
