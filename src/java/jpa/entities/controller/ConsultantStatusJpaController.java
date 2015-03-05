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
import jpa.entities.ConsultantStatus;
import jpa.entities.controller.exceptions.NonexistentEntityException;
import jpa.entities.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author lucas.freire
 */
public class ConsultantStatusJpaController implements Serializable {

    public ConsultantStatusJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConsultantStatus consultantStatus) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(consultantStatus);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConsultantStatus(consultantStatus.getStatusId()) != null) {
                throw new PreexistingEntityException("ConsultantStatus " + consultantStatus + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConsultantStatus consultantStatus) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            consultantStatus = em.merge(consultantStatus);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Character id = consultantStatus.getStatusId();
                if (findConsultantStatus(id) == null) {
                    throw new NonexistentEntityException("The consultantStatus with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Character id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConsultantStatus consultantStatus;
            try {
                consultantStatus = em.getReference(ConsultantStatus.class, id);
                consultantStatus.getStatusId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The consultantStatus with id " + id + " no longer exists.", enfe);
            }
            em.remove(consultantStatus);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ConsultantStatus> findConsultantStatusEntities() {
        return findConsultantStatusEntities(true, -1, -1);
    }

    public List<ConsultantStatus> findConsultantStatusEntities(int maxResults, int firstResult) {
        return findConsultantStatusEntities(false, maxResults, firstResult);
    }

    private List<ConsultantStatus> findConsultantStatusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConsultantStatus.class));
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

    public ConsultantStatus findConsultantStatus(Character id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConsultantStatus.class, id);
        } finally {
            em.close();
        }
    }

    public int getConsultantStatusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConsultantStatus> rt = cq.from(ConsultantStatus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
