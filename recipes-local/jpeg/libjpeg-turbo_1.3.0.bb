DESCRIPTION = "libjpeg-turbo is a derivative of libjpeg that uses SIMD instructions (MMX, SSE2, NEON) to accelerate baseline JPEG compression and decompression"
HOMEPAGE = "http://libjpeg-turbo.org/"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://cdjpeg.h;endline=12;md5=78fa8dbac547bb5b2a0e6457a6cfe21d \
                    file://jpeglib.h;endline=14;md5=a08bb0a80f782a9f8da313cc4015ed6f \
                    file://djpeg.c;endline=13;md5=03db065044529233c296324be85530ab \
"

SRC_URI = "http://sourceforge.net/projects/libjpeg-turbo/files/${PV}/libjpeg-turbo-${PV}.tar.gz"

SRC_URI[md5sum] = "e1e65cc711a1ade1322c06ad4a647741"
SRC_URI[sha256sum] = "2657008cfc08aadbaca065bd9f8964b8a2c0abd03e73da5b5f09c1216be31234"

# Drop-in replacement for jpeg
PROVIDES = "jpeg"
RPROVIDES_${PN} += "jpeg"
RREPLACES_${PN} += "jpeg"
RCONFLICTS_${PN} += "jpeg"

inherit autotools pkgconfig

# EXTRA_OECONF = "--with-jpeg8 "

PACKAGES =+ "jpeg-tools libturbojpeg"

DESCRIPTION_jpeg-tools = "The jpeg-tools package includes the client programs for access libjpeg functionality.  These tools allow for the compression, decompression, transformation and display of JPEG files."
FILES_jpeg-tools = "${bindir}/*"

FILES_libturbojpeg = "${libdir}/libturbojpeg.so.*"

BBCLASSEXTEND = "native"
DEPENDS = "nasm-native"
