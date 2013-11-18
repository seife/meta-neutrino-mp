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

SRC_URI[md5sum] = "a77c23a233f6471abe73bfa8c026ee39"
SRC_URI[sha256sum] = "a68aaaf3c43a7af5f324e68b0a4e6a7b3e7e2109a0fd6db19f86e642dea5bc4a"

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
