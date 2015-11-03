# needed when adding to Jenkins:
# cd python/jenkins-testing
PYTHONPATH=''
nosetests --with-xunit --all-modules --traverse-namespace --with-coverage --cover-package=./ --cover-inclusive
python -m coverage xml --include=./*
pylint -f parseable -d I0011,R0801 *.py | tee pylint.out
