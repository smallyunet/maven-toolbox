
git add -A
if [[ $1 == "" ]]
then
    desc="shell auto commit"
else
    desc=$1
fi
git commit -m "$desc"
git push
