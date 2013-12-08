# Base this image on core-image-minimal
include recipes-core/images/core-image-minimal.bb

# 488M + 20MB /boot +4MB alignment = 512M
IMAGE_ROOTFS_SIZE = "499712"

IMAGE_DEPENDS += " \
	e2fsprogs \
"

IMAGE_INSTALL += " \
	neutrino-mp \
	strace \
	procps \
	image-config \
	e2fsprogs-mke2fs \
	e2fsprogs-e2fsck \
	e2fsprogs-tune2fs \
	djmount \
	autofs \
"

# Tripledragon Kernel is too old for udev
IMAGE_INSTALL += "${@'udev-extraconf' if MACHINE != 'tripledragon' else ''}"

# Include modules in rootfs, but not on coolstream, where
# flash is small...
IMAGE_INSTALL += "${@'kernel-modules' if MACHINE != 'coolstream' else ''}"

EXTRAOPKGCONFIG = "neutrino-feed-config"
