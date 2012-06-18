#include <iostream>
#include <fstream>
#include <string>
#include "quotes.pb.h"
using namespace std;

// Iterates though all people in the AddressBook and prints info about them.
void ListQuotes(const quotes::Quote& qlist) {
  for (int i = 0; i < qlist._size(); i++) {
    const quotes::Quote& q = qlist.quote(i);

    cout << "Q=" << q.quote() << endl;
}

// Main function:  Reads the entire address book from a file and prints all
//   the information inside.
int main(int argc, char* argv[]) {
  // Verify that the version of the library that we linked against is
  // compatible with the version of the headers we compiled against.
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  if (argc != 2) {
    cerr << "Usage:  " << argv[0] << " ADDRESS_BOOK_FILE" << endl;
    return -1;
  }

  quotes::Quote q;

  {
    // Read the existing address book.
    fstream input(argv[1], ios::in | ios::binary);
    if (!address_book.ParseFromIstream(&input)) {
      cerr << "Failed to parse address book." << endl;
      return -1;
    }
  }

  ListQuotes(address_book);

  // Optional:  Delete all global objects allocated by libprotobuf.
  google::protobuf::ShutdownProtobufLibrary();

  return 0;
}
