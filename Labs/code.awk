BEGIN {
  include = 1;
  inStudentToWriteBlock = 0;
}
{ 
  if ( (inStudentToWriteBlock == 0) && /\@exclude/) {
    inStudentToWriteBlock = 1;
    print "\t//TODOBEGIN(EE422C)";
  } else if ( (inStudentToWriteBlock == 1) && /\@include/) { 
    inStudentToWriteBlock = 0;
    print "\n\t//TODOEND(EE422C)";
  }
  if ( !inStudentToWriteBlock && !/.\@include/) {
    print $0;
  }
}
