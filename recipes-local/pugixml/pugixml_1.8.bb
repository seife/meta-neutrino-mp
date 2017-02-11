SUMMARY = "pugixml is a light-weight C++ XML processing library."
HOMEPAGE = "http://pugixml.org/"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://readme.txt;md5=2be9cf2e0549f2bf41eea05baf50a409 \
"

SRC_URI = " \
	http://github.com/zeux/pugixml/releases/download/v1.8/pugixml-1.8.tar.gz \
	file://pugixml-config.patch \
"

SRC_URI[md5sum] = "ffa59ee4853958e243050e6b690b4f2e"
SRC_URI[sha256sum] = "8ef26a51c670fbe79a71e9af94df4884d5a4b00a2db38a0608a87c14113b2904"

inherit cmake

#EXTRA_OECMAKE += "-DBUILD_SHARED_LIBS:BOOL=ON"

FILES_${PN}-dev += "${libdir}/cmake/pugixml/*"
