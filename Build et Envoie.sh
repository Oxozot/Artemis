#!/usr/bin/env bash
set -euo pipefail

########## PARAMÈTRES À PERSONNALISER ##########
# Machine distante
REMOTE_USER="thomas"
REMOTE_HOST="192.168.1.40"       # IP ou nom DNS

REMOTE_DIR="Documents/ServerPaper/plugins/"   # Dossier de destination du JAR

# tmux : nom de la session où tourne la console du serveur
TMUX_SESSION="minecraft"

# Projet Gradle local
GRADLE_CMD="gradle"   # ou simplement "gradle" si vous avez Gradle installé
###############################################

# 1. Build Gradle — génère le JAR dans build/libs
echo "▶️  Compilation Gradle…"
$GRADLE_CMD clean build -x test

# 2. Repère le dernier JAR produit
JAR_PATH=$(ls -t build/libs/*.jar | head -n1)
echo "✅  JAR généré : $JAR_PATH"  # ex. build/libs/monplugin-1.0.0.jar

# 3. Copie vers le serveur
echo "▶️  Transfert du JAR vers ${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_DIR}…"
scp "$JAR_PATH" "${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_DIR}"

## 4. Envoie la commande 'rl' dans la session tmux distante
#echo "▶️  Reload dans tmux…"
#ssh -T "${REMOTE_USER}@${REMOTE_HOST}" <<EOF
#tmux send-keys -t "${TMUX_SESSION}" "rl" C-m
#EOF

echo "🟢  Terminé !"
