#!/bin/tcsh
foreach f (`ls *.c`)
  echo $f
  enscript -fCourier6 -2r -G -p$f.ps $f
  ps2pdf $f.ps
end
gs -dNOPAUSE -sDEVICE=pdfwrite \
 -sOUTPUTFILE=sources.pdf -dBATCH *.pdf
