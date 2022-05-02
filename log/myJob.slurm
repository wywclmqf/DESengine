#!/bin/bash
#SBATCH -A rabl
#SBATCH -p magic
#SBATCH --mem 80G
#SBATCH --cpus-per-task=30 
#SBATCH -v
srun -w node-01 tools/java/bin/java -cp Desis.jar EntryPoint.EnteryPointForRootnode 1
srun -w node-02 tools/java/bin/java -cp Desis.jar EntryPoint.EnteryPointForIntermedianode 1
