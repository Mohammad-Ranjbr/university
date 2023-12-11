package com.example.Final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Final.Entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer>{

}
