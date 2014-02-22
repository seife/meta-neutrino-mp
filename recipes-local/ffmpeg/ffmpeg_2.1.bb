PV = "2.1.3"

require ffmpeg.inc

SRC_URI = "http://www.ffmpeg.org/releases/ffmpeg-${PV}.tar.bz2;name=ffmpeg-${PV}"

SRC_URI[ffmpeg-2.1.3.md5sum] = "711b795bbc7b527c0f4a1828f324fd5a"
SRC_URI[ffmpeg-2.1.3.sha256sum] = "1d332e7fd35f87e1ffb6c9b0405cbfda085ef712ede0133a213793a4e66d13a3"

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
