#include <iostream>
#include <string>

class Point {
public :
  int x;
  int y;
  Point() { this->x = -1; this->y = -1; }
  Point(int x, int y ) { this->x = x; this->y = y; }
  char *toString() { char tmpbuf[100]; sprintf(tmpbuf, "%d,%d\n", x, y ); return tmpbuf; }
};


int foo(Point p) {
  p.x = 20;
  p.y = 30;
}

int main(int argc, char **argv) {
  Point p;
  p.x = 1;
  p.y = 2;
  printf("%s", p.toString());
  foo(p);
  printf("%s", p.toString());
}
