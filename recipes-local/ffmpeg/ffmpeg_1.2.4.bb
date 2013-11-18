##
## the binary only libs from coolstream are built with / against lightly patched ffmpeg-1.2.x
## to ensure binary compatibility, take no chances and just build the same version instead of 2.x
##

require ffmpeg.inc

SRC_URI = " \
	http://ffmpeg.org/releases/ffmpeg-1.2.4.tar.bz2 \
	file://0007-libavformat-utils.c-comment-symlink-to-av_gettime-on.patch \
	file://0008-ffmpeg-add-ASF-VC1-Annex-G-and-RCV-bitstream-filters.patch \
	file://0009-ffmpeg-disable-timestamp-modification-for-H264-it-sc.patch \
"

SRC_URI[md5sum] = "f1cfdbaae308214beeb621746300de16"
SRC_URI[sha256sum] = "3b96c8ce1b86575cd74f540e5b7a52681554289017d4b974e4efad2a09290ddf"

LIC_FILES_CHKSUM = " \
	file://COPYING.GPLv2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
	file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504 \
	file://COPYING.LGPLv2.1;md5=bd7a443320af8c812e4c18d1b79df004 \
	file://COPYING.LGPLv3;md5=e6a600fd5e1d9cbde2d983680233ad02 \
"

EXTRA_OECONF += " \
	--enable-postproc \
"

PROVIDES = "ffmpeg_${PV}"
