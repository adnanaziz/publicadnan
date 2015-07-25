_cmd() { 
    local cur files
    COMPREPLY=()
    cur="${COMP_WORDS[COMP_CWORD]}"
    #echo "${cur}"
    # files=$( ls src/main/java/com/epi | grep java | sed -e "s/^ -Dtest=TestAll#/"  )  
    files=$( ls src/main/java/com/epi | grep java | sed -e "s/\.java//" | awk '{print "-Dtest=TestAll#"$1}' )
    #echo "${files}"
    COMPREPLY=( $(compgen -W "${files}" -- ${cur}) )
}

#complete -F _cmd mvn
complete -F _cmd mvn
