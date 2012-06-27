
SNAPPYDIR = ./snappy-1.0.5

						# $(SNAPPYDIR)/snappy_unittest-snappy-test.o $(SNAPPYDIR)/snappy_unittest-snappy_unittest.o
SNAPPYOBJS = $(SNAPPYDIR)/snappy-c.o $(SNAPPYDIR)/snappy.o $(SNAPPYDIR)/snappy-sinksource.o $(SNAPPYDIR)/snappy-stubs-internal.o 

CFLAGS = -O4 -fpermissive


mem: mem.c
	gcc -O4 -o mem mem.c -lpthread

textlzo: testlzo.c
	g++ $(CFLAGS)  -c  testlzo.c minilzo-2.06/minilzo.c -Iminilzo-2.06 -I$(SNAPPYDIR); \
	g++ $(CFLAGS)  -o testlzo testlzo.o minilzo-2.06/minilzo.o -Iminilzo-2.06 $(SNAPPYOBJS)  -I$(SNAPPYDIR) 


SRCDIR=.
DSTDIR=.

quotes.pb.cc: quotes.proto
	@echo $(SRCDIR); protoc -I=$(SRC_DIR) --cpp_out=$(DST_DIR) $(SRC_DIR)/quotes.proto

QuotesWrite: quoteswrite.cc quotes.pb.cc
	g++ -o QuotesWrite quoteswrite.cc quotes.pb.cc -lprotobuf

QuotesRead: quotesread.cc quotes.pb.cc
	g++ -o QuotesRead quotesread.cc quotes.pb.cc -lprotobuf

AddressBookWrite: AddressBookWrite.cc addressbook.pb.cc
	g++ -o AddressBookWrite AddressBookWrite.cc addressbook.pb.cc -lprotobuf

AddressBookRead: AddressBookRead.cc addressbook.pb.cc
	g++ -o AddressBookRead AddressBookRead.cc addressbook.pb.cc -lprotobuf

addressbook.pb.cc: addressbook.pb
	protoc -I=$(SRC_DIR) --cpp_out=$(DST_DIR) $(SRC_DIR)/addressbook.proto
