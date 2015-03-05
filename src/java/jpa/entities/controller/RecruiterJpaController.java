/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.entities.Recruiter;
import jpa.entities.controller.exceptions.NonexistentEntityException;
import jpa.entities.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author lucas.freire
 */
public class RecruiterJpaController implements Serializable {

    public RecruiterJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recruiter recruiter) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(recruiter);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRecruiter(recruiter.getRecruiterId()) != null) {
                throw new PreexistingEntityException("Recruiter " + recruiter + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Recruiter recruiter) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            recruiter = em.merge(recruiter);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = recruiter.getRecruiterId();
                if (findRecruiter(id) == null) {
                    throw new NonexistentEntityException("The recruiter with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recruiter recruiter;
            try {
                recruiter = em.getReference(Recruiter.class, id);
                recruiter.getRecruiterId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recruiter with id " + id + " no longer exists.", enfe);
            }
            em.remove(recruiter);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Recruiter> findRecruiterEntities() {
        return findRecruiterEntities(true, -1, -1);
    }

    public List<Recruiter> findRecruiterEntities(int maxResults, int firstResult) {
        return findRecruiterEntities(false, maxResults, firstResult);
    }

    private List<Recruiter> findRecruiterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Recruiter.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Recruiter findRecruiter(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Recruiter.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecruiterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recruiter> rt = cq.from(Recruiter.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
