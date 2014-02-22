##
## the binary only libs from coolstream are built with / against lightly patched ffmpeg-1.2.x
## to ensure binary compatibility, take no chances and just build the same version instead of 2.x
##

PV = "1.2.5"

require ffmpeg.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/ffmpeg-1.2:"

SRC_URI = " \
	http://ffmpeg.org/releases/ffmpeg-${PV}.tar.bz2;name=ffmpeg-${PV} \
	file://0007-libavformat-utils.c-comment-symlink-to-av_gettime-on.patch \
	file://0008-ffmpeg-add-ASF-VC1-Annex-G-and-RCV-bitstream-filters.patch \
	file://0009-ffmpeg-disable-timestamp-modification-for-H264-it-sc.patch \
"

SRC_URI[ffmpeg-1.2.4.md5sum] = "f1cfdbaae308214beeb621746300de16"
SRC_URI[ffmpeg-1.2.4.sha256sum] = "3b96c8ce1b86575cd74f540e5b7a52681554289017d4b974e4efad2a09290ddf"
SRC_URI[ffmpeg-1.2.5.md5sum] = "57e04df3c2da3e1d42341313ad5cb642"
SRC_URI[ffmpeg-1.2.5.sha256sum] = "9cfcdfe1949f8781b1b473fe668b8f2ab6befc2cffefd159840c62273e3e41a9"

LIC_FILES_CHKSUM = " \
	file://COPYING.GPLv2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
	file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504 \
	file://COPYING.LGPLv2.1;md5=bd7a443320af8c812e4c18d1b79df004 \
	file://COPYING.LGPLv3;md5=e6a600fd5e1d9cbde2d983680233ad02 \
"

PROVIDES = "ffmpeg_${PV}"
