#dot test.dot -Tpng -o test.png -Tcmapx -o test.map
dot -Tcmapx -ox.map -Tpng -ograph.png graph.dot
#convert adnan-new-90.png -alpha set -gravity center -extent 120x120 \
#          badge_mask.png -compose DstIn -composite \
#          badge_shading.png -compose Over -composite \
#          badge_trans_bg.png

#convert -size 200x200 xc:none -fill adnan-new.jpg -draw "circle 50,50 100,1" circle_thumb.png
