#include <iostream>
#include <fstream>
#include <string>
#include "quotes.pb.h"
#include <stdio.h>
using namespace std;

// This function fills in a Person message based on user input.
void addquotes(quotes::QuoteList* ql) {
  FILE *fp = fopen("q.txt", "r");
  double p;
  while ( EOF != fscanf(fp, "%lf", & p ) ) {
    ql->add_quote(p);
    // printf("processed %lf\n", p );
  }
  fclose(fp);
}

int main(int argc, char* argv[]) {
  // Verify that the version of the library that we linked against is
  // compatible with the version of the headers we compiled against.
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  if (argc != 2) {
    cerr << "Usage:  " << argv[0] << " SER_PRICES_FILE_NAME" << endl;
    return -1;
  }

  quotes::QuoteList ql;

  addquotes(&ql);

  {
    // Write the new quote list back to disk.
    fstream output(argv[1], ios::out | ios::trunc | ios::binary);
    if (!ql.SerializeToOstream(&output)) {
      cerr << "Failed to write quote file." << endl;
      return -1;
    }
  }

  // Optional:  Delete all global objects allocated by libprotobuf.
  google::protobuf::ShutdownProtobufLibrary();

  return 0;
}
