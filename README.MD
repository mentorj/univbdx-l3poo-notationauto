# Notation automatique projet L3POO
Ce repository contient le code necessaire a la notation
automatique du TP note pour les L3POO.
Le code devra etre integre dans Moodle...

## Tests realises
Sous Linux avec JDK Openjdk8u222
## Ce qui est fait 
Differents sujets couverts:

- compilation automatique du code du projet et evaluation de celle-ci
- examen des imports
- examen des signatures de methodes (throw MaxSizeExceededException)
- examen de la presence des classes attendues
- examen des classes (abstraites ou pas) et des methodes (abstraites ou pas)

# Ce qui n'a pas ete fait
Il manque encore:

- lancement de la methode getTotalCost(), une utilisation massive de l'instrospection permet de valider cela
- examen des attributs (Qdox permet cela aisement)
- adapter les scripts:
  - enlever  l'appel a Javac devenu inutile
  - ajouter la dependance qdox
- lancement sur tous les projets etudiants
- formattage des resultats
- affiner les points attribues par test unitaire (Mark)
- cleanup du code & du projet 