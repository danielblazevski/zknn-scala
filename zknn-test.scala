import scala.collection.mutable.ArrayBuffer

import zknn.zknn

object zknnTest{

  def benchmark_zknn(numPoints: Int){
    /// generate random set of points in [1,2]^6
    // to-do allow for negative entries, even when shifted in zknn query
    val r = scala.util.Random

    val training = ArrayBuffer.fill(numPoints)(Array(r.nextDouble + 1.0, r.nextDouble + 1.0,
      r.nextDouble + 1.0,r.nextDouble + 1.0,r.nextDouble + 1.0))

    val testing = ArrayBuffer.fill(numPoints)(Array(r.nextDouble + 1.0, r.nextDouble + 1.0,
      r.nextDouble + 1.0,r.nextDouble + 1.0,r.nextDouble + 1.0))
      
    val alpha = 2
    val gamma = 5

    val t0 = System.nanoTime()
    val ZknnClass = new zknn(alpha, gamma)
    val zknn = ZknnClass.zknnQuery(training, testing, 1)
    val tf = System.nanoTime()
    println("Elapsed time for zknn =       : " + (tf - t0)/1000000000 + "s")

    val t0_brute = System.nanoTime()
    val knn = ZknnClass.basicknnQuery(training, testing, 1)
    val tf_brute = System.nanoTime()
    println("Elapsed time for brute force knn =       : " + (tf_brute - t0_brute)/1000000000 + "s")

  }

  def main(args: Array[String]) {

    val alpha = 1
    val gamma = 1

    val ZknnClass = new zknn(alpha, gamma)

    val lb = Array(2.0, 6.0)
    val Zval = ZknnClass.zValue(lb)
    println("zVal =  " + Zval)

    val train = ArrayBuffer(Array(1.2, 4.3), Array(25.0, 3.0), Array(29.0, 7.5))
    val test = ArrayBuffer(Array(0.8, 3.5), Array(1.6, 0.2))

    //val knn = ZknnClass.zknnQuery(train, test, 1)
    //println("nearest neighbor =  " + knn.head._2.head)

    val numPoints = 64000
     benchmark_zknn(numPoints)

  }
}
