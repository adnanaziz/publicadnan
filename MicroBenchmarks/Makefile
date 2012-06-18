SRCDIR = .
DSTDIR = .

quotes.pb.o: quotes.proto
	protoc -I=$(SRC_DIR) --cpp_out=$(DST_DIR) $(SRC_DIR)/quotes.proto

QuotesWrite: quoteswrite.cc quotes.pb.cc
	g++ -o QuotesWrite quoteswrite.cc quotes.pb.cc -lprotobuf

QuotesRead: quotesread.cc quotes.pb.cc
	g++ -o QuotesRead quotesread.cc quotes.pb.cc -lprotobuf

AddressBookWrite: AddressBookWrite.cc addressbook.pb.cc
	g++ -o AddressBookWrite AddressBookWrite.cc addressbook.pb.cc -lprotobuf

AddressBookRead: AddressBookRead.cc addressbook.pb.cc
	g++ -o AddressBookRead AddressBookRead.cc addressbook.pb.cc -lprotobuf

addressbook.pb.o: addressbook.pb
	protoc -I=$(SRC_DIR) --cpp_out=$(DST_DIR) $(SRC_DIR)/addressbook.proto
