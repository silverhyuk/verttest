/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

vertx.createNetClient.connect(8080, "localhost", { asyncResult: AsyncResult[NetSocket] =>
  if (asyncResult.succeeded()) {
    val socket = asyncResult.result()
    socket.dataHandler({ buffer: Buffer =>
      container.logger.info("Net client receiving: " + buffer)
    })

    //Now send some data
    for (i <- 0 until 10) {
      val str = "hello" + i + "\n"
      container.logger.info("Net client sending: " + str)
      socket.write(Buffer(str))
    }
  } else {
    asyncResult.cause.printStackTrace()
  }
})