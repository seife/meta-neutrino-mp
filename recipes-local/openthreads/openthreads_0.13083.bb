SUMMARY = "OpenThreads is a cross platform, object orientated threading library."
DESCRIPTION = "OpenThreads is a cross platform, object orientated threading library."
SECTION = "libs"
LICENSE = "OSGPL"
LIC_FILES_CHKSUM = "file://COPYING.txt;md5=9226151d58bcdf987ed14e7dc8cedcbc \
"
DEPENDS = ""
SRCDATE = "${PV}"
PR = "r1"

SRC_URI = "https://gitorious.org/neutrino-hd/buildsystem-cs/raw/archive-patches/sources/OpenThreads-svn-13083.tar.lzma \
           file://002-omit-policy-cmp0014.patch;pnum=0 \
"

SRC_URI[md5sum] = "e3e1853207acbc65a9f4f008851125fe"
SRC_URI[sha256sum] = "a745a62950689b9cf39c8213047a8f0403ebd82c488be301578d7b6906615184"

S = "${WORKDIR}/OpenThreads-svn-13083/"

do_unpack() {
	lzcat ${DL_DIR}/OpenThreads-svn-13083.tar.lzma | tar x
}

inherit cmake 

EXTRA_OECMAKE += "-DCMAKE_BUILD_TYPE=Release \
                  -DCMAKE_SYSTEM_NAME=Linux \
                  -D_OPENTHREADS_ATOMIC_USE_GCC_BUILTINS_EXITCODE=1 \
                  -D_OPENTHREADS_ATOMIC_USE_GCC_BUILTINS=1 \
"

ARM_INSTRUCTION_SET = "arm"
