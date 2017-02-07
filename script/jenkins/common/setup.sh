
set -eux

REPOSITORY_URL="git@github.com:tnejime/JenkinsTest"
BRANCH_NAME="${BRANCH_NAME:-"master"}"

if ! git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
  git init
fi

git config remote.origin.url "${REPOSITORY_URL}"
git config --local include.path ../.gitconfig

if git rev-parse "refs/remotes/origin/${BRANCH_NAME}^{commit}" >/dev/null 2>&1;then
  REVISION=$(git rev-parse "refs/remotes/origin/${BRANCH_NAME}^{commit}")
else
  REVISION=$(git rev-parse "${BRANCH_NAME}^{commit}")
fi

git checkout -f "${REVISION}"
git clean -dfx
git pull
