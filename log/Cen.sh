for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalCenDesisPunTum$i
rm -rf ../log/outputrootCenDesisPunTum$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisPunTum.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputlocalCenDesisPunTum$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisPunTum.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputrootCenDesisPunTum$i &

sleep 300

scancel -u wang.yue

sleep 20
done

for i in 2 10 50 100 500 1000
do

echo $i	
rm -rf ../log/outputlocalCenDesisPunPun$i
rm -rf ../log/outputrootCenDesisPunPun$i
srun -A rabl -p magic -w node-07 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisPunPun.jar De.Hpi.DesisCen.EntryPoint.EntryPointForLocalNode 4 $i > ../log/outputlocalCenDesisPunPun$i &

srun -A rabl -p magic -w node-08 --mem 40G --cpus-per-task=20 ../tools/java/bin/java -Xms2G -Xmx40G -cp ../DesisCen/DesisPunPun.jar De.Hpi.DesisCen.EntryPoint.EnteryPointForRootnode 1 $i > ../log/outputrootCenDesisPunPun$i &

sleep 300

scancel -u wang.yue

sleep 20
done

echo END!!!
