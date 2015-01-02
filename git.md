# Astuce GIT

## Extraire un dossier d'un dépôt comme un nouveau dépôt

[source](http://www.duchatelet.net/blog/?post/2013/05/30/Extraire-un-dossier-d-un-d%C3%A9p%C3%B4t-Git%2C-et-en-faire-un-autre-d%C3%A9p%C3%B4t)

* suppression de origin pour éviter les bêtises

    ```bash
    $ git remote rm origin
    ```

* extraction : on ne garde que le dossier `sql` et son historique :

    ```bash
    $ git filter-branch --subdirectory-filter sql -- --all
    ```

* un peu de propre

    ```bash
    $ git reset --hard
    $ git gc --aggressive --prune=now
    ```

* ajout du nouveau remote

    ``bash
    $ git remote add origin <url>
    ```

## Pour récupérer rapidement l'historique d'un répertoire (NEW WAY)

[source](http://stackoverflow.com/questions/359424/detach-subdirectory-into-separate-git-repository/17864475#17864475)

```bash
git subtree split -P btoa -b btoa-only
```

prend l'historique du dossier btoa et le stocke dans la branche btoa-only


