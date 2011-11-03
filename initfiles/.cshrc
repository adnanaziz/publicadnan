# need this otherwise sftp won't work
# http://discussions.apple.com/thread.jspa?messageID=8215297
if (! $?prompt ) exit

set path = ( /usr/texbin /sbin /usr/sbin /usr/local/bin /usr/bin /bin /usr/local/bin /usr/X11R6/bin . )

# always get the hostname because it is used later in various ways
if ( ! $?hostname ) then
    set full_hostname = `/bin/hostname`
    set hostname      = `echo $full_hostname | /usr/bin/sed -e 's/\..*//'`
endif

if ( $?prompt ) then
    set prompt = "$hostname \!: " 

    set notify 
    set history = 100
    set savehist = 100
    limit coredumpsize 0

    # this is done in the .login
    # set mail = ( 120 /usr/spool/mail/$user )
    
    # Prevent logout accidents
    set ignoreeof

    # Users like and understand filec
    set filec

    # This is useful for jobs in the background
    set notify
    
endif

# in case something wants to pop up an editor.
#setenv EDITOR vi

# For diehard vi fans, uncomment out this line.
setenv EDITOR /usr/bin/vi
setenv SVN_EDITOR /usr/bin/vi
setenv EXINIT 'set noterse sw=4'

# Be sure to place temporary files in the correct directory.
# The use of /tmp should be minimal to nil.

source ~/.aliases
