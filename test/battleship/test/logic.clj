(ns battleship.test.logic
  (:require [battleship.core :as core])
  (:use clojure.test
        battleship.logic))


(deftest test-register-new-game
  (let [battlefields (atom {})]
    (testing "Adding new name into empty global game context"
      (let [actual (register-new-game battlefields)]
        (is (not= actual nil))
        (is (= (count @battlefields) 1))))
    (testing "Adding new name into an not empty global game context"
      (let [actual (register-new-game battlefields)]
        (is (not= actual nil))
        (is (= (count @battlefields) 2))))))

(deftest test-shoot-enemy
  (let [battlefield (atom [{:has-enemy? true  :shot-by "player1"}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "player1"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "playerX"}
                           {:has-enemy? true  :shot-by "playerX2"}
                           {:has-enemy? true  :shot-by "playerX2"}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "player1"}])]
    (testing "5x5 matrice: Shoot enemy in cell: row=2 col=1"
      (let [actual (shoot-enemy 2 1 "plx" battlefield)]
        (is (= (:shot-by (core/get-cell 2 1 actual)) "plx" ))))))

(deftest test-attempt-attack
  (let [battlefield (atom [{:has-enemy? true  :shot-by "player1"}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "player1"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "playerX"}
                           {:has-enemy? true  :shot-by "playerX2"}
                           {:has-enemy? true  :shot-by "playerX2"}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "player1"}])]
    (testing "5x5 matrice: Fire on enemy located at row=0 col=0"
      (let [actual (attempt-attack 0 0 "plx" battlefield)]
        (is (= actual :failure))))

    (testing "5x5 matrice: Fire on enemy located at row=0 col=1"
      (let [actual (attempt-attack 0 1 "plx" battlefield)]
        (is (= actual :failure))))

    (testing "5x5 matrice: Fire on enemy located at row=0 col=2"
      (let [actual (attempt-attack 0 2 "plx" battlefield)]
        (is (= actual :success))))))

(deftest test-launck-attack
  (let [battlefield (atom [{:has-enemy? true  :shot-by "player1"}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "player1"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? true  :shot-by "player2"}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "player1"}
                           {:has-enemy? true  :shot-by "playerX2"}
                           {:has-enemy? true  :shot-by "playerX2"}
                           {:has-enemy? true  :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? false :shot-by :none}
                           {:has-enemy? true  :shot-by "player1"}])]
    (testing "5x5 matrice: Fire on enemy located at row=0 col=0"
      (let [actual (launch-attack 0 0 "plx" battlefield)]
        (is (= actual {:attack-status :failure :game-status :running :score {"player1" 4 "player2" 3 "playerX2" 2}} ))))

    (testing "5x5 matrice: Fire on enemy located at row=0 col=1"
      (let [actual (launch-attack 0 1 "plx" battlefield)]
        (is (= actual {:attack-status :failure :game-status :running :score {"player1" 4 "player2" 3 "playerX2" 2}}))))

    (testing "5x5 matrice: Fire on enemy located at row=0 col=2"
      (let [actual (launch-attack 0 2 "plx" battlefield)]
        (is (= actual {:attack-status :success :game-status :running :score {"player1" 4 "player2" 3 "playerX2" 2 "plx" 1}}))))

    (testing "5x5 matrice: Fire on enemy located at row=4 col=0"
      (let [actual (launch-attack 4 0 "plx" battlefield)]
        (is (= actual {:attack-status :success :game-status :over :score {"player1" 4 "player2" 3 "playerX2" 2 "plx" 2}}))))))

(deftest test-generate-game-id
  (testing "Generates a random identifier."
    (let [actual (generate-game-id)]
      (is (not= actual nil))
      (is (false? (.isEmpty actual))))))

(deftest test-show-context
  (testing "5x5 matrices: 2 games context"
    (let [actual (show-global-context (atom {"game-id1" (atom [{:has-enemy? true  :shot-by "player1"}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? true  :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? true  :shot-by "player1"}
                                                               {:has-enemy? true  :shot-by "player2"}
                                                               {:has-enemy? true  :shot-by "player2"}
                                                               {:has-enemy? true  :shot-by "player2"}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? true  :shot-by "playerX"}
                                                               {:has-enemy? true  :shot-by "playerX2"}
                                                               {:has-enemy? true  :shot-by "playerX2"}
                                                               {:has-enemy? true  :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? true  :shot-by "player1"}])

                                             "game-id2" (atom [{:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? true  :shot-by "pl3"}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? true  :shot-by "pl1"}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? true  :shot-by "pl1"}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}
                                                               {:has-enemy? false :shot-by :none}])}))]
      (is (= (count actual) 2))
      (is (= (nth actual 0) {"game-id1" {"player1" 3 "player2" 3 "playerX" 1 "playerX2" 2 :status :running}}))
      (is (= (nth actual 1) {"game-id2" {"pl3" 1 "pl1" 2 :status :over}})))))

(deftest test-terminated-gamnes
  (testing "5x5 matrices: 3 games context, 1 running and 2 terminated"
    (let [actual (terminated-games (atom {"game-id1" (atom [{:has-enemy? true  :shot-by "player1"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "player1"}
                                                            {:has-enemy? true  :shot-by "player2"}
                                                            {:has-enemy? true  :shot-by "player2"}
                                                            {:has-enemy? true  :shot-by "player2"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "playerX"}
                                                            {:has-enemy? true  :shot-by "playerX2"}
                                                            {:has-enemy? true  :shot-by "playerX2"}
                                                            {:has-enemy? true  :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "player1"}])

                                          "game-id2" (atom [{:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "pl3"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "pl1"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "pl1"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}])

                                          "game-id3" (atom [{:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "pl3"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "pl1"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? true  :shot-by "pl1"}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}
                                                            {:has-enemy? false :shot-by :none}])}))]
      (is (= (count actual) 2))
      (is (= actual  ["game-id2" "game-id3"])))))

(deftest test-gc
  (testing "5x5 matrices: 3 games context, 1 running and 2 terminated"
    (let [actual (gc (atom { "game-id1" (atom [{:has-enemy? true  :shot-by "player1"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "player1"}
                                               {:has-enemy? true  :shot-by "player2"}
                                               {:has-enemy? true  :shot-by "player2"}
                                               {:has-enemy? true  :shot-by "player2"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "playerX"}
                                               {:has-enemy? true  :shot-by "playerX2"}
                                               {:has-enemy? true  :shot-by "playerX2"}
                                               {:has-enemy? true  :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "player1"}])

                             "game-id2" (atom [{:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "pl3"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "pl1"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "pl1"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}])

                             "game-id3" (atom [{:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "pl3"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "pl1"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? true  :shot-by "pl1"}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}
                                               {:has-enemy? false :shot-by :none}])}))]
      (is (= (count actual) 1))
      (is (= (first (keys actual)) "game-id1"))
      (is (= @(first (vals actual)) [{:has-enemy? true  :shot-by "player1"}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? true  :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? true  :shot-by "player1"}
                                     {:has-enemy? true  :shot-by "player2"}
                                     {:has-enemy? true  :shot-by "player2"}
                                     {:has-enemy? true  :shot-by "player2"}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? true  :shot-by "playerX"}
                                     {:has-enemy? true  :shot-by "playerX2"}
                                     {:has-enemy? true  :shot-by "playerX2"}
                                     {:has-enemy? true  :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? false :shot-by :none}
                                     {:has-enemy? true  :shot-by "player1"}])))))
