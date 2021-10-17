SUMMARY = "pugixml is a light-weight C++ XML processing library."
HOMEPAGE = "http://pugixml.org/"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://readme.txt;md5=a631ae00f08e5904238e3a2653d66404 \
"

SRC_URI = " \
	http://github.com/zeux/pugixml/releases/download/v1.11/pugixml-1.11.tar.gz \
	file://pugixml-config.patch \
"

SRC_URI[md5sum] = "93540f4644fd4e4b02049554ef37fb90"
SRC_URI[sha256sum] = "26913d3e63b9c07431401cf826df17ed832a20d19333d043991e611d23beaa2c"

inherit cmake

EXTRA_OECMAKE += "-DBUILD_SHARED_LIBS:BOOL=ON"

FILES_${PN}-dev += "${libdir}/cmake/pugixml/*"
